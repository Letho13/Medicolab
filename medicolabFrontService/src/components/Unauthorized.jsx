import React from 'react';
import { useNavigate } from 'react-router-dom';

const Unauthorized = () => {
    const navigate = useNavigate();

    return (
        <div style={{ textAlign: 'center', padding: '4rem' }}>
            <h1 style={{ fontSize: '2rem', color: '#e63946' }}>⛔ Accès non autorisé</h1>
            <p style={{ marginTop: '1rem' }}>
                Vous devez être connecté pour accéder à cette page.
            </p>
            <button
                style={{
                    marginTop: '2rem',
                    padding: '0.75rem 1.5rem',
                    backgroundColor: '#1d3557',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer'
                }}
                onClick={() => navigate('/login')}
            >
                Retour à la connexion
            </button>
        </div>
    );
};

export default Unauthorized;