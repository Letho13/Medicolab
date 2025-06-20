import React from "react";

const Header = ({ toggleModal, nbOfPatients }) => {
    return (
        <header>
            <h1>Gestion Patients ({nbOfPatients})</h1>
        </header>
    );
};

export default Header;
