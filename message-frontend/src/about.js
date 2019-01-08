import React, { Component } from 'react';

class About extends Component {
    constructor(props) {
        super(props);
        this.toggleHover = this.toggleHover.bind(this);
        this.state = {
            hover: false
        }
    }

    toggleHover() {
        const hover = this.state.hover;
        this.setState({ hover: !hover });
    }

    render() {
        const cssClass = this.state.hover ? "showAboutContent" : "hideAboutContent";
        return (
            <div className="aboutButtonContainer">
                <div className={cssClass}>
                    <p align="left">
                        This Global Map displays the number of transactions originating from a country in terms of
                        percentage of total transactions.
                        This helps visualize our userbase in a geographical context.
                        This data is displayed in real time as each transaction is being processed at the back end.<br /><br />
                        The percentage associated with each country is represened on a color scale from red to green
                        where red is the lowest (0%) and green is the highest(100%)
                       <br />
                        For example: If there are 4 transactions so far from the US alone, its percentage is 100%, and is
                        represented in <span style={{ 'color': '#00a400' }}>green.</span><br />
                        If the next (5th) transaction originated in India, the percentage for India is calculated as:
                        (1/5)*100 = 20%, and will be represented as a shade of <span style={{ 'color': '#b60000' }}> red</span>, while US will change from <span style={{ 'color': '#00a400' }}>bright green(100%)</span> to a
                        slightly more  <span style={{ 'color': '#878c00' }}>red shade (80%)</span>
                    </p>

                    <h5>
                        NOTE: due to hosting restrictions, the server might disconnect the websocket connection
                        if it is idle. This is not a limitation of the developed system. Refresh the frontend to re-establish
                        the connection.
                    </h5>
                    <br />
                </div>
                <div className="aboutButton" onMouseEnter={this.toggleHover} onMouseLeave={this.toggleHover}>
                    ABOUT
                </div>
            </div>
        );
    }
}
export default About;