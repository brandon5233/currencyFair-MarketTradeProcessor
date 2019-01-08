import React, { Component } from 'react';
import { VectorMap } from 'react-jvectormap';
class MapComponent extends Component {
    
    render() {
        return (
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
                    series={{
                        regions: [
                            {
                                values: {}
                            }
                        ]
                    }}
                />
            </div>
        );
    }

    componentDidUpdate() {
        const mapObject = this.refs.map.getMapObject();
        mapObject.series.regions[0].setValues(this.props.originData);
    }
}

export default MapComponent