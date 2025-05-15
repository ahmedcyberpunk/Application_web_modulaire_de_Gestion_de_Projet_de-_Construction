from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import joblib
import numpy as np

# Load model and scaler
model = joblib.load("knn_model.pkl")
scaler = joblib.load("scaler.pkl")

app = FastAPI()

# ✅ Add CORS middleware here
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:4200"],  # Angular dev server
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Define request body
class PredictionInput(BaseModel):
    size: float
    floors: int
    location_factor: float
    material_cost: float
    labor_cost: float
    contingency_rate: float

@app.post("/predict")
def predict(data: PredictionInput):
    input_data = np.array([[ 
        data.size,
        data.floors,
        data.location_factor,
        data.material_cost,
        data.labor_cost,
        data.contingency_rate
    ]])
    
    input_scaled = scaler.transform(input_data)
    prediction = model.predict(input_scaled)[0]
    
    return {"estimated_budget": round(prediction, 2)}
