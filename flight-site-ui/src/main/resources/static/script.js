const map = L.map('map').setView([51.505, -0.09], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

document.addEventListener('DOMContentLoaded', function () {
    fetchFlightDataAndDisplay(map);
});

function fetchFlightDataAndDisplay(map, aircraftModel = []) {
    let url = 'http://localhost:8081/flights/all';
    if (aircraftModel.length > 0) {
        const modelParam = aircraftModel.map(model => 'aircraft_icao=' + encodeURIComponent(model)).join('&');
        url += '?' + modelParam;
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            clearMarkers(map);
            data.forEach(function (dataPoint) {
                    if (dataPoint.lat && dataPoint.lng) {
                        L.marker([dataPoint.lat, dataPoint.lng])
                            .addTo(map)
                            .bindPopup(
                                'Flight: ' + (dataPoint.flight_icao || 'Unknown') +
                                '<br>Aircraft: ' + (dataPoint.aircraft_icao || 'N/A') +
                                '<br>Altitude: ' + (dataPoint.alt + ' m'|| 'N/A') +
                                '<br>Speed: ' + (dataPoint.speed + ' km/h' || 'N/A')
                            );
                    }
                });
        })
        .catch(error => console.error('Could not fetch data: ', error));
}


document.addEventListener('DOMContentLoaded', function () {
    fetchFlightDataAndDisplay(map);
    const aircraftFilter = document.getElementById('aircraftFilter');

    aircraftFilter.addEventListener('change', function () {
        const selectedAircraft = this.value;
        let aircraftModel = [];

        switch (selectedAircraft) {
            case 'B73':
                aircraftModel = ['B73', 'B38M'];
                break;
            case 'A320':
                aircraftModel = ['A320', 'A20N'];
                break;
            case 'A35':
                aircraftModel = ['A35'];
                break;
            default:
                aircraftModel = [];
        }
        clearMarkers(map);
        fetchFlightDataAndDisplay(map, aircraftModel)
    })
})
/*document.getElementById('aircraftFilter').addEventListener('change', function(e) {
    const selectedAircraft =  e.target.value;
    let aircraftModel = [];

    if (selectedAircraft === 'B73') {
        aircraftModel = ['B73', 'B38M'];
    } else if (selectedAircraft === 'A320') {
        aircraftModel = ['A320', 'A20N'];
    } else if (selectedAircraft === 'A321') {
        aircraftModel = ['A321', 'A21N']
    } else if (selectedAircraft === 'E17') {
        aircraftModel = ['E17', 'E75']
    } else if (selectedAircraft === 'E19') {
        aircraftModel = ['E19', 'E29']
    }
    clearMarkers(map);
    fetchFlightDataAndDisplay(map, aircraftModel);
});*/


function clearMarkers(map) {
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

/*map.on('moveend', function() {
    var bounds = map.getBounds();
    fetchFlightsInRange(map, bounds);
});

function fetchFlightsInRange(map, bounds) {
    var url = `http://localhost:8081/flights/bounds?minLat=${bounds.getSouth()}&maxLat=${bounds.getNorth()}&minLng=${bounds.getWest()}&maxLng=${bounds.getEast()}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            //
        })
        .catch(error => console.error('Could not get data: ', error))
}*/