import Pizza from "./Pizzas";
import { useState, useEffect } from "react";

export default function Menu() {
    const [pizzas, setPizzas] = useState([
        {
            name: "Focaccia",
            ingredients: "Bread with italian olive oil and rosemary",
            price: 6,
            photoName: "/img/pizzas/1.jpg",
            soldOut: false,
        },
        {
            name: "Pizza Margherita",
            ingredients: "Tomato and mozarella",
            price: 10,
            photoName: "/img/pizzas/2.jpg",
            soldOut: false,
        },
        {
            name: "Pizza Spinaci",
            ingredients: "Tomato, mozarella, spinach, and ricotta cheese",
            price: 12,
            photoName: "/img/pizzas/3.jpg",
            soldOut: false,
        },
        {
            name: "Pizza Funghi",
            ingredients: "Tomato, mozarella, mushrooms, and onion",
            price: 12,
            photoName: "/img/pizzas/4.jpg",
            soldOut: false,
        },
        {
            name: "Pizza Salamino",
            ingredients: "Tomato, mozarella, and pepperoni",
            price: 15,
            photoName: "/img/pizzas/5.jpg",
            soldOut: true,
        },
        {
            name: "Pizza Prosciutto",
            ingredients: "Tomato, mozarella, ham, aragula, and burrata cheese",
            price: 18,
            photoName: "/img/pizzas/6.jpg",
            soldOut: false,
        },
    ])

    useEffect(() => {
        const data = localStorage.getItem("pizzas");
        if(data) setPizzas(JSON.parse(data))
    }, [])

    useEffect(() => localStorage.setItem("pizzas", JSON.stringify(pizzas)), [pizzas])

    const deletePizza = (pizzaName) =>
        setPizzas(prev => prev.filter((pizza) => pizza.name !== pizzaName));

    return (
        <main className="menu">
            <h2>Our menu</h2>
            {pizzas.length > 0 ? (
                // este fragmento <> </> se conoce como fragment, y no se renderiza en la aplicación,
                // nos sirve como contenedor padre pero sin ser renderizado en el html
                <>
                    <p>
                        Authentic Italian cuisine. 6 creative dishes to choose from.
                        All from our stone oven, all organic all delicious.
                    </p>

                    <ul className="pizzas">
                        {pizzas.map((pizza) => {
                            return (
                                <Pizza pizzaObject={pizza} key={pizza.name} onDelete={deletePizza}/>
                            )
                        })}
                    </ul>
                </>
            ) : (
                <p>We're still working on our menu. Please come back later.</p>
            )}
        </main>
    );

}