import React from 'react';
import { Navigate } from 'react-router-dom';
import { getToken } from '../api/AuthService';

const PrivateRoute = ({ children }) => {
    const token = getToken();
    return token ? children : <Navigate to="/unauthorized" replace />;
};

export default PrivateRoute;