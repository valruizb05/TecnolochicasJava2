import './App.css'
import Header from './components/Header'
//import Card from './components/Card'
import Pizzas from './components/Pizzas'
import Menu from './components/Menu'
import Footer from './components/Footer'
import { Routes, Route } from 'react-router-dom';  
import Home from './Pages/Home'
import Owner from './Pages/Owner'
import Navbar from './components/Navbar'


function App() {

  return (
    <div>
      <Header />
      <Navbar />
      <Menu />
      {/*<Card />*/}
      <Footer />
      <Routes>
        <Route path="/" element={<Home />} />,
        <Route path="/Owner" element={<Owner />} />
      </Routes>
    </div>
  )
}

export default App
