import axiosInstance from "./axiosConfig";

const RISQUE_API_PATH = `/RISQUE-SERVICE/api/risque`;

console.log("Appel API vers :", RISQUE_API_PATH);

export async function getAnalyseByPatientId(id) {
    return await axiosInstance.get(`${RISQUE_API_PATH}/analyse/${id}`);
}