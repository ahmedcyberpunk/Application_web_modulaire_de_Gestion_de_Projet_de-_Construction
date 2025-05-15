from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from collections import defaultdict
import numpy as np

app = Flask(__name__)
CORS(app)

# Locations dataset with lat/lon
locations_data = {
    "Tunis": (36.8065, 10.1815),
    "Sfax": (34.7390, 10.7600),
    "Bizerte": (37.2744, 9.8739),
    "Sousse": (35.8254, 10.6360),
    "Gabès": (33.8815, 10.0982),
    "Kairouan": (35.6615, 10.0965),
    "Monastir": (35.7643, 10.8190),
    "Nabeul": (36.4570, 10.7380),
    "Zaghouan": (36.4065, 10.1030),
    "Tataouine": (32.9300, 10.4730),
    "Tozeur": (33.9191, 8.1290),
    "Beja": (36.7251, 9.1813),
    "Kasserine": (35.1665, 8.8329),
    "Medenine": (33.3578, 10.5042),
    "Manouba": (36.7167, 10.1000),
    "Ariana": (36.8667, 10.1667),
    "Gafsa": (34.4233, 8.7763),
    "Siliana": (36.1000, 9.3833),
    "Jendouba": (36.5000, 8.7833),
    "Mahdia": (35.5044, 11.0625)
}

# Convert dictionary to DataFrame
locations = pd.DataFrame([{"name": name, "latitude": lat, "longitude": lon}
                          for name, (lat, lon) in locations_data.items()])

# Train KNN model
knn = NearestNeighbors(metric='euclidean')
knn.fit(locations[["latitude", "longitude"]])

# In-memory storage for search counts
search_counts = defaultdict(int)

@app.route('/recommend', methods=['GET'])
def recommend():
    location_name = request.args.get('location')

    # Validate request
    if not location_name:
        return jsonify({"error": "Location parameter is missing"}), 400

    if location_name not in locations["name"].values:
        return jsonify({"error": "Location not found"}), 404

    # Update search count
    search_counts[location_name] += 1

    # Find all locations (we'll filter out the queried one)
    index = locations[locations["name"] == location_name].index[0]
    distances, indices = knn.kneighbors(
        [locations.loc[index, ["latitude", "longitude"]]],
        n_neighbors=len(locations)
    )

    # Prepare results, excluding the queried location
    recommendations = []
    for i in range(len(indices[0])):
        loc_idx = indices[0][i]
        loc_name = locations.iloc[loc_idx]["name"]
        distance = distances[0][i]

        if loc_name != location_name:  # Skip the queried location
            recommendations.append({
                "location": loc_name,
                "distance": float(np.round(distance, 4))  # Round to 4 decimal places
            })

    # Return recommendations sorted by distance (closest first)
    return jsonify({
        "query_location": location_name,
        "recommendations": sorted(recommendations, key=lambda x: x["distance"])
    })

@app.route('/popular', methods=['GET'])
def get_popular_locations():
    # Sort locations by search count (descending)
    popular = sorted(search_counts.items(), key=lambda x: x[1], reverse=True)
    return jsonify([{"location": loc, "count": cnt} for loc, cnt in popular])

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)