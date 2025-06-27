// src/Pages/Owner.jsx
import React, { useState } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../components/styles/owner.css'; // crea este archivo para tus estilos

export default function Owner() {
    // Estado para la lista de películas
    const [movies] = useState([
        'El Padrino',
        'La vida es bella',
        'Interestelar',
        'Parásitos',
        'Amélie'
    ]);

    return (
        <>
            <Header />

            <main className="owner-container">
                {/* Sección de datos personales */}
                <section className="profile">
                    <img
                        src="/assets/img/profile.jpg"   // pon aquí tu foto o un placeholder
                        alt="Owner"
                        className="profile-img"
                    />
                    <h1 className="owner-name">Valeria Ruiz</h1>
                    <p className="owner-contact">
                        📧 <a href="mailto:valeria.ruiz@example.com">valeria.ruiz@example.com</a>
                    </p>
                    <p className="owner-description">
                        Desarrolladora web apasionada por crear experiencias digitales
                        intuitivas y estéticamente atractivas.
                    </p>
                </section>

                {/* Sección de películas favoritas */}
                <section className="favorite-movies">
                    <h2>Mis películas favoritas</h2>
                    <ul>
                        {movies.map((title, idx) => (
                            <li key={idx}>{title}</li>
                        ))}
                    </ul>
                </section>
            </main>

            <Footer />
        </>
    );
}
