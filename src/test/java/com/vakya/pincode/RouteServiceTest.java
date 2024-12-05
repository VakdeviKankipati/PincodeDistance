package com.vakya.pincode;

import com.vakya.pincode.models.Route;
import com.vakya.pincode.repositories.RouteRepository;
import com.vakya.pincode.services.RouteService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {
    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @Mock
    private RestTemplate restTemplate;

    private String fromPincode = "141106";
    private String toPincode = "110060";
    private String jsonResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample JSON response that will be used for parsing
        jsonResponse = "{\n" +
                "  \"routes\": [\n" +
                "    {\n" +
                "      \"legs\": [\n" +
                "        {\n" +
                "          \"distance\": {\n" +
                "            \"text\": \"353 km\",\n" +
                "            \"value\": 353376\n" +
                "          },\n" +
                "          \"duration\": {\n" +
                "            \"text\": \"6 hours 7 mins\",\n" +
                "            \"value\": 22001\n" +
                "          },\n" +
                "          \"start_address\": \"Halwara, Punjab 141106, India\",\n" +
                "          \"end_address\": \"New Delhi, Delhi 110060, India\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @Test
    public void testGetRoute_CacheHit(){
        // Arrange: Set up the mock RouteRepository to return a cached route
        Route cachedRoute = new Route();
        cachedRoute.setFromPincode(fromPincode);
        cachedRoute.setToPincode(toPincode);
        cachedRoute.setDistance(353.376);
        cachedRoute.setDuration("6 hours 7 mins");

        when(routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode))
                .thenReturn(Optional.of(cachedRoute));

        // Act: Call the service method
        Route route = routeService.getRoute(fromPincode, toPincode);

        // Assert: Ensure that the cached route is returned without making an API call
        assertNotNull(route, "Route should not be null");
        assertEquals(fromPincode, route.getFromPincode());
        assertEquals(toPincode, route.getToPincode());
        assertEquals(353.376, route.getDistance());
        assertEquals("6 hours 7 mins", route.getDuration());

        // Verify that no external API call is made (RestTemplate is not used)
        verify(restTemplate, times(0)).getForObject(anyString(), eq(String.class));
    }


}
