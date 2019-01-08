import React, { Component } from 'react';
import './App.css';
import WebSocketWorker from './infostream.js';
import MapComponent from './map-component'
import Navbar from './navbar'
import DisplayPercents from './country-percent';
import ConnectionStatus from './connection-status';
import About from './about';
import Help from './help'

class App extends Component {

  constructor(props) {
    super(props);
    this.updateOriginCountryPercents = this.updateOriginCountryPercents.bind(this);
    this.updateOriginCountryGradients = this.updateOriginCountryGradients.bind(this);
    this.changeConnectionStatus = this.changeConnectionStatus.bind(this);
    this.toggleHelp = this.toggleHelp.bind(this);
    this.state = {
      originCountryPercents: {},
      originCountryGradients: {},
      isServerConnected: false,
      showHelp: false

    };
  }

  changeConnectionStatus(newState) {
    this.setState({ isServerConnected: newState });
  }

  updateOriginCountryPercents(originCountryPercents) {
    this.setState({ originCountryPercents: originCountryPercents });
  }

  updateOriginCountryGradients(originCountryGradients) {
    this.setState({ originCountryGradients: originCountryGradients });
  }

  toggleHelp() {
    const help = this.state.showHelp;
    this.setState({ showHelp: !help });
  }

  render() {
    const classHelp = this.state.showHelp ? "showHelp" : "hideHelp";
    return (
      <div className="App">

        <div className="navbar-container">
          <Navbar
            connectionStatus={this.state.isServerConnected}
            toggleHelp={this.toggleHelp}
          />
        </div>

        <div className="mapContainer">

          <MapComponent originData={this.state.originCountryGradients} />

          <Help classHelp={classHelp} />

          <div className="mapLegendContainer">
            <img className="mapLegend" src={'/MapLegend.png'} />
          </div>

          <div className="infoContainer">
            <DisplayPercents originCountryPercents={this.state.originCountryPercents} />
          </div>

          <div className="connectionStatusContainer">
            <ConnectionStatus isServerConnected={this.state.isServerConnected} />
          </div>

          <div className="aboutContainer">
            <About />
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
