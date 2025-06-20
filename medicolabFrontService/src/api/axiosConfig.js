import axios from "axios";
import {getToken, login, logout} from "./AuthService";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8222';

const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
});


axiosInstance.interceptors.request.use(
    (config) => {
        const token = getToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
        });

axiosInstance.interceptors.response.use(
    response => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const username = import.meta.env.VITE_LOGIN_USERNAME;
                const password = import.meta.env.VITE_LOGIN_PASSWORD;

                const newTokenData = await login(username, password);
                localStorage.setItem('token', newTokenData.token);


                originalRequest.headers['Authorization'] = `Bearer ${newTokenData.token}`;

                return axiosInstance(originalRequest);
            } catch (loginError) {
                logout();
                window.location.href = '/unauthorized'; // ou autre redirection
                return Promise.reject(loginError);
            }
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;