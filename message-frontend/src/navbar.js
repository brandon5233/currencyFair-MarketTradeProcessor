import React, { Component } from 'react';
import './navbar.css';

class Navbar extends Component{

    render(){
        return(
            <div className="navbar">
               <h2 id="logo">currencyfair</h2>
               <h3 id="description">message-frontend component</h3>
            </div>
        );
    }
}

export default Navbar;