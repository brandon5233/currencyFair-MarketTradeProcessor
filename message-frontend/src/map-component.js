import React, { Component } from 'react';
import {VectorMap} from 'react-jvectormap';
import './map-component.css';


const  mapData = {
    IN: "200", //Red
    US: "0" //Green
  };

  

class MapComponent extends Component{
    constructor(props){
        super(props);
    }

    handleClick= (name) => {
    }

    
    render(){
        console.log("from map component");
        console.log(mapData);
        
        return(
            <div className="Map">
            <VectorMap map={'world_mill'}
                       backgroundColor="#3b96ce"
                       zoomOnScroll={false}
                       ref="map"
                       containerStyle={{
                           width: '100%',
                           height: '100%'
                       }}
                       containerClassName="map"
                       onRegionClick={this.handleClick.bind(this)}
                       series={{
                        regions: [
                          {
                            //values:JSON.parse(JSON.stringify(this.state.mapData)),  
                           values:{}
                           //scale: ["#146804", "#ff0000"],  
                           //normalizeFunction: 'polynomial'
                            
                          }
                        ]
                      }}
            />
        </div>
        );
    }
    
   componentDidUpdate(){
        const mapObject = this.refs.map.getMapObject();
        const newScale = {scale: ["#146804", "#ff0000"]};
        mapObject.series.regions[0].setValues(this.props.originData);
   }
}

export default MapComponent