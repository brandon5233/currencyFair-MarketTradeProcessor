import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import WebSocketWorker from './infostream.js';
import MapComponent from './map-component'
import Navbar from './navbar'
import DisplayPercents from './country-percent';
import ConnectionStatus from './connection-status';

class App extends Component {

  constructor(props){
    super(props);
    this.updateOriginCountryPercents = this.updateOriginCountryPercents.bind(this);
    this.updateOriginCountryGradients = this.updateOriginCountryGradients.bind(this);
    this.changeConnectionStatus = this.changeConnectionStatus.bind(this);
    this.state = {
      originCountryPercents : {},
      originCountryGradients : {},
      isServerConnected : false,
    };
  }

  changeConnectionStatus(newState){
    this.setState({isServerConnected : newState});
  }

  updateOriginCountryPercents(originCountryPercents){
    this.setState({originCountryPercents:originCountryPercents});
  }

  updateOriginCountryGradients(originCountryGradients){
    this.setState({originCountryGradients : originCountryGradients});
  }

  render() {
    console.log("state data :")
    console.log(this.state.originCountryGradients);
    return (
      
      <div className="App">

        <div className="navbar-container">
        <Navbar connectionStatus={this.state.isServerConnected}/>
        </div>

        <div className = "mapContainer">
          <MapComponent originData={this.state.originCountryGradients}/>  
          <div className="mapLegendContainer">
            <img className="mapLegend" src={'/MapLegend.png'}/>
          </div>
          <div className="infoContainer">
            <DisplayPercents originCountryPercents={this.state.originCountryPercents}/>
          </div>

          <div className="connectionStatusContainer">
            <ConnectionStatus isServerConnected={this.state.isServerConnected}/>
          </div>

          <div className="WebSocketListener">
          <WebSocketWorker 
            messages={this.state.messages} 
            updateOriginCountryPercents={this.updateOriginCountryPercents} 
            updateOriginCountryGradients={this.updateOriginCountryGradients}
            changeConnectionStatus={this.changeConnectionStatus} />
            </div>
        </div>
        
      
       
      </div>
    )
  }
}

export default App;
