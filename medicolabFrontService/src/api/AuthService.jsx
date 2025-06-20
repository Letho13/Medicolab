import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8222';
const API_URL = `${API_BASE_URL}`;


export async function login(username, password) {
    const response = await axios.post(`${API_URL}/login`, { username, password });
    const data = response.data;
    localStorage.setItem('token', data.token);
    return data;
}

export function getToken() {
    return localStorage.getItem('token');
}

export function logout() {
    localStorage.removeItem('token');
}

export async function fetchProtectedData(endpoint) {
    const token = getToken();
    if (!token) {
        throw new Error('Aucun token trouvé, veuillez vous connecter');
    }
    const response = await axios.get(`${API_BASE_URL}${endpoint}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
}