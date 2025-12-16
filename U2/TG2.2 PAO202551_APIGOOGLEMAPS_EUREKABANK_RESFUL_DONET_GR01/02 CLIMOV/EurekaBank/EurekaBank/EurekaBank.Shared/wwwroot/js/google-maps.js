
// Basic Google Maps Loader and Interop
export class GoogleMapsInterop {
    static async loadScript(apiKey) {
        if (window.google?.maps) return;

        return new Promise((resolve, reject) => {
            const script = document.createElement('script');
            script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&libraries=places&language=es`;
            script.async = true;
            script.defer = true;
            script.onload = resolve;
            script.onerror = reject;
            document.head.appendChild(script);
        });
    }

    static async initializeMap(elementId, lat, lng, zoom) {
        if (!window.google?.maps) {
            throw new Error("Google Maps API not loaded");
        }

        const mapOptions = {
            center: { lat: parseFloat(lat), lng: parseFloat(lng) },
            zoom: zoom,
        };

        const element = document.getElementById(elementId);
        if (!element) return null;

        this.map = new google.maps.Map(element, mapOptions);

        // Add marker for initial position
        this.destinationMarker = new google.maps.Marker({
            position: mapOptions.center,
            map: this.map,
            title: "Ubicación"
        });

        this.directionsService = new google.maps.DirectionsService();
        this.directionsRenderer = new google.maps.DirectionsRenderer();
        this.directionsRenderer.setMap(this.map);
    }

    static async calculateRoute(destLat, destLng, originLat, originLng) {
        let origin;

        if (originLat && originLng) {
            // Use explicit origin
            origin = {
                lat: parseFloat(originLat),
                lng: parseFloat(originLng)
            };
            return this._performRouteCalculation(origin, destLat, destLng);
        } else {
            // Check for Secure Context
            if (!window.isSecureContext && window.location.hostname !== 'localhost') {
                throw new Error("Geolocalización requiere HTTPS. En desarrollo móvil, habilite 'Insecure origins treated as secure' en chrome://flags.");
            }

            // Use geolocation
            if (!navigator.geolocation) {
                throw new Error("Geolocation not supported");
            }

            return new Promise((resolve, reject) => {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        origin = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        this._performRouteCalculation(origin, destLat, destLng)
                            .then(resolve)
                            .catch(reject);
                    },
                    (error) => {
                        let msg = "Error obteniendo ubicación: ";
                        switch (error.code) {
                            case error.PERMISSION_DENIED:
                                msg += "Permiso denegado.";
                                break;
                            case error.POSITION_UNAVAILABLE:
                                msg += "Información de ubicación no disponible.";
                                if (!window.isSecureContext) msg += " (Posible error de HTTPS)";
                                break;
                            case error.TIMEOUT:
                                msg += "Tiempo de espera agotado.";
                                break;
                            default:
                                msg += error.message;
                        }
                        reject(msg);
                    }
                );
            });
        }
    }

    static _performRouteCalculation(origin, destLat, destLng) {
        return new Promise((resolve, reject) => {
            const destination = {
                lat: parseFloat(destLat),
                lng: parseFloat(destLng)
            };

            const request = {
                origin: origin,
                destination: destination,
                travelMode: google.maps.TravelMode.DRIVING
            };

            this.directionsService.route(request, (result, status) => {
                if (status === 'OK') {
                    this.directionsRenderer.setDirections(result);

                    // Extract relevant route info
                    const route = result.routes[0];
                    const leg = route.legs[0];

                    const routeInfo = {
                        distance: leg.distance.text,
                        duration: leg.duration.text,
                        startAddress: leg.start_address,
                        endAddress: leg.end_address,
                        steps: leg.steps.map(step => ({
                            instruction: step.instructions,
                            distance: step.distance.text,
                            duration: step.duration.text
                        }))
                    };

                    resolve(JSON.stringify(routeInfo));
                } else {
                    reject("No se pudo calcular la ruta: " + status);
                }
            });
        });
    }

    static speakText(text, dotNetHelper) {
        this.stopSpeaking(); // Stop any previous speech

        if ('speechSynthesis' in window) {
            const utterance = new SpeechSynthesisUtterance(text);

            // Robust Voice Selection logic
            let voices = window.speechSynthesis.getVoices();

            // If voices are not yet loaded, wait for the event (Chrome quirk)
            if (voices.length === 0) {
                window.speechSynthesis.onvoiceschanged = () => {
                    this._speakWithVoice(text, dotNetHelper);
                };
                return; // Will be called async
            }

            this._speakWithVoice(text, dotNetHelper);
        }
    }

    static _speakWithVoice(text, dotNetHelper) {
        const utterance = new SpeechSynthesisUtterance(text);
        const voices = window.speechSynthesis.getVoices();

        // Find best Spanish voice
        // Priority: 'es-ES' -> 'es-MX' -> any 'es'
        let selectedVoice = voices.find(v => v.lang === 'es-ES' || v.lang === 'es_ES');
        if (!selectedVoice) {
            selectedVoice = voices.find(v => v.lang.startsWith('es'));
        }

        if (selectedVoice) {
            utterance.voice = selectedVoice;
            utterance.lang = selectedVoice.lang;
            // console.log("Using voice:", selectedVoice.name, selectedVoice.lang);
        } else {
            // Fallback
            utterance.lang = 'es-ES';
        }

        if (dotNetHelper) {
            utterance.onend = () => {
                dotNetHelper.invokeMethodAsync('OnSpeechEnded');
            };
        }

        window.speechSynthesis.speak(utterance);
    }

    static stopSpeaking() {
        if ('speechSynthesis' in window) {
            window.speechSynthesis.cancel();
        }
    }
}

window.GoogleMapsInterop = GoogleMapsInterop;
