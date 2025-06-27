export default function Card() {
    const cardStyle = {
        width: 300,
        backgroundColor: "#ffc0cb",
        paddingTop: 20,
        paddingBottom: 20,
    }

    const imgStyle = {
        width: 200,
    }

    const pStyle = {
        color: "#ffffff",
        fontSize: 30,
        
    }

     const aStyle = {
        color: "#ffffff",
        
    }

    return (
        <header>
            <div style={cardStyle}>
                <img style={imgStyle} src="https://images.unsplash.com/photo-1627157766791-4eeb96934b5d?q=80&w=765&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="" />
                <p style={pStyle}>Valeria Ruiz | Desarrolladora</p>
                <a style={aStyle} href="https://www.linkedin.com/in/valruizb/">LINKEDLN</a>    <br />
                <a style={aStyle} href="https://github.com/valruizb05">GITHUB</a>
            </div>
        </header>
    )
}