import axiosInstance from "./axiosConfig";


const NOTE_API_PATH = `/NOTE-SERVICE/api/note`;

export async function saveNote(note) {
    return await axiosInstance.post(`${NOTE_API_PATH}/add`, note);
}

export async function getNotes(page = 0, size = 10) {
    return await axiosInstance.get(NOTE_API_PATH);
}

export async function getNotesByPatientId(id) {
    return await axiosInstance.get(`${NOTE_API_PATH}/patient/${id}`);
}

// export async function updatePatient(patient) {
//     return await axios.put(API_URL, patient);
// }

export async function deleteNote(id) {
    return await axiosInstance.delete(`${NOTE_API_PATH}/delete/${id}`);
}