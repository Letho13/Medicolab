import axiosInstance from "./axiosConfig";


const PATIENT_API_PATH  = "/PATIENT-SERVICE/api/patient";


export async function savePatient(patient) {
    return await axiosInstance.post(`${PATIENT_API_PATH}/add`, patient);
}

export async function getPatients(page = 0, size = 10) {
    return await axiosInstance.get(PATIENT_API_PATH);
}

export async function getPatient(id) {
    return await axiosInstance.get(`${PATIENT_API_PATH}/${id}`);
}

export async function updatePatient(patient) {
    return await axiosInstance.put(PATIENT_API_PATH, patient);
}

export async function deletePatient(id) {
    return await axiosInstance.delete(`${PATIENT_API_PATH}/delete/${id}`);
}