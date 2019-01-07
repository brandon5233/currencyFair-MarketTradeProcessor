import React, { Component } from 'react';

class DisplayPercents extends Component{

    
    render(){
        console.log(this.props.originPercents);
        const dataArray = this.props.originCountryPercents || {};
        console.log(dataArray)
        return(
            
            <div className="displaypercents">
               <div className="percentBoxHeader">
                    <h5>% Transactions per country</h5>
               </div>
                {
                    Object.keys(dataArray)
                    .sort((country1, country2) => dataArray[country1] < dataArray[country2])
                    .map((country, index) => <p key={index} className="message">{country} : {dataArray[country]}%</p>)
                }
            </div>
        );
    }
}

export default DisplayPercents;