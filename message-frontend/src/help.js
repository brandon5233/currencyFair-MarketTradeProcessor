import React from 'react'

function Help(props) {
    return (
        <div align="left" className={props.classHelp} >
            Post data to the endpoint API while the frontend is connected to the server to view
            data being processed in real time.
            <h6>
                NOTE: due to hosting restrictions, the server might disconnect the websocket connection
                if it is idle. This is not a limitation of the developed system. Refresh the frontend to re-establish
                the connection.
            </h6>
        </div>
    );
}



export default Help;