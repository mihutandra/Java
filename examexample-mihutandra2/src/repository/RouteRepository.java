package repository;

import domain.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RouteRepository {
    public List<Route> routes = new ArrayList<>();

    public void init(){
        Route s1 =  new Route("Roma","Berlin","13:00","15:00",130,154);
        Route s2 = new Route("Paris","Bucharest","11:00","14:00",220,50);
        Route s3 = new Route("Bucharest","Cluj-Napoca","07:00","09:00",33,20);
        for (Route r: Arrays.asList(s1,s2,s3)) {
            routes.add(r);
        }
    }

    // Add a route to the repository
    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    // Get all routes from the repository
    public List<Route> getAllRoutes() {
        return new ArrayList<>(routes);
    }

    // Get routes sorted by departure city and time
    public List<Route> getRoutesSorted() {
        List<Route> sortedRoutes = routes.stream()
                .sorted((r1, r2) -> {
                    if (r1.getSourceCity().equals(r2.getSourceCity())) {
                        return r1.getDepartureTime().compareTo(r2.getDepartureTime());
                    } else {
                        return r1.getSourceCity().compareTo(r2.getSourceCity());
                    }
                })
                .toList();

        return List.copyOf(sortedRoutes); // Return an immutable copy
    }

}
