import React from 'react';

function ConnectionStatus(props){
    const displayStatus = (status) => {
        if(status){
            return(
                <h5 className="serverConnected">Server Connected!</h5>
            );
        }else{
            return(
                <div className="serverDisconnected">
                    <h5 >Disconnected!</h5>
                    <h5 className="refreshNotice">Refresh the page to reconnect</h5>
                </div>
            );
        }
    }
    return(
        <div className="connectionStatus">
            {displayStatus(props.isServerConnected)}
        </div>
    );
}



export default ConnectionStatus;