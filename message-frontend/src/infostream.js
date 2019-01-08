import React, { Component } from 'react';

class WebSocketWorker extends Component {
    constructor(props) {
        super(props);
        this.updateState = this.updateState.bind(this);
        this.state = {
            m: { IN: 0 }
        }
        this.establlishSocketConnection();
    }

    establlishSocketConnection() {
        //const socket = new WebSocket("ws://0.0.0.0:4567/infostream");
        const socket = new WebSocket("ws://heroku-spark-backend.herokuapp.com/infostream");
        socket.onopen = () => {
            this.props.changeConnectionStatus(true);
        }
        socket.onmessage = (message) => {
            try {
                console.log(JSON.parse(message.data).originCountryPercents)
                this.updateState(JSON.parse(message.data).originCountryGradients);
                this.props.updateOriginCountryGradients(JSON.parse(message.data).originCountryGradients);
                this.props.updateOriginCountryPercents(JSON.parse(message.data).originCountryPercents);
            } catch (e) {
                console.log(e);
            }
        }
        socket.onclose = () => {
            this.props.changeConnectionStatus(false);
        }
    }

    updateState(newM) {
        this.setState({ m: newM });
    }

    render() {

        return (
            <div>
            </div>
        );
    }
}

export default WebSocketWorker;
