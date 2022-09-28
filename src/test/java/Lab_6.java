import com.RainCarnation.NeuralNetworkKohonen;
import com.RainCarnation.service.Measurable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Lab_6 {
    public static class District implements Measurable {
        private final double X;
        private final double Y;

        private final String name;

        public District(double x, double y, String name) {
            this.X = x;
            this.Y = y;
            this.name = name;
        }

        @Override
        public double getX() {
            return X;
        }

        @Override
        public double getY() {
            return Y;
        }

        @Override
        public String getId() {
            return name;
        }
    }

    public static class Hospital implements Measurable {
        private final double X;
        private final double Y;
        private final String name;
        private final String admArea;

        public Hospital(double x, double y, String name_, String admArea_) {
            this.X = x;
            this.Y = y;
            this.name = name_;
            this.admArea = admArea_;
        }

        @Override
        public double getX() {
            return X;
        }

        @Override
        public double getY() {
            return Y;
        }

        @Override
        public String getId() {
            return name;
        }

        @Override
        public String getArea() {
            return admArea;
        }
    }

    private static Hospital[] parseJsonToHospitals(String filename) {
        JSONParser parser = new JSONParser();
        Hospital[] hospitals = null;
        try (Reader reader = new FileReader(filename)) {
            JSONArray hospitalsJ = (JSONArray) parser.parse(reader);
            hospitals = new Hospital[hospitalsJ.size()];
            JSONObject hospital, geoData;
            JSONArray coordinates, district;
            double x, y;
            String name, area;
            for (int i = 0; i < hospitalsJ.size(); ++i) {
                hospital = (JSONObject) hospitalsJ.get(i);
                name = (String) hospital.get("FullName");

                district = (JSONArray) hospital.get("ObjectAddress");
                area = (String) ((JSONObject) district.get(0)).get("AdmArea");

                geoData = (JSONObject) hospital.get("geoData");
                coordinates = (JSONArray) ((JSONArray) geoData.get("coordinates")).get(0);
                x = (double) coordinates.get(1);
                y = (double) coordinates.get(0);

                hospitals[i] = new Hospital(x, y, name, area);
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
        return hospitals;
    }

    private static District[] parseJsonToDistricts(String filename) {
        JSONParser parser = new JSONParser();
        District[] districts = null;

        try (Reader reader = new FileReader(filename)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray districtsJ = (JSONArray) object.get("Districts");
            JSONObject district;

            String name;
            double x, y;
            districts = new District[districtsJ.size()];
            for (int i = 0; i < districts.length; ++i) {
                district = (JSONObject) districtsJ.get(i);
                name = (String) district.get("FullName");
                x = (double) ((JSONArray) (district.get("Coordinates"))).get(0);
                y = (double) ((JSONArray) (district.get("Coordinates"))).get(1);
                districts[i] = new District(x, y, name);
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        return districts;
    }

    public static void main(String[] args) throws Exception {
        Hospital[] hospitals = parseJsonToHospitals("src/test/resources/Hospitals.json");
        District[] districts = parseJsonToDistricts("src/test/resources/Districts.json");

        Writer writer = new FileWriter("results/lab_6.txt");
        writer.write("==========================   Больницы Москвы   ==========================\n");

        NeuralNetworkKohonen<Hospital, District> network = new NeuralNetworkKohonen<>();
        network.fit(hospitals, districts);

        double counter = 0;
        District district;
        for (Hospital hospital: hospitals) {
            district = network.getResult(hospital);
            writer.write(hospital.getId() + ": " + district.getId());
            if (district.getId().equals(hospital.getArea())) {
                writer.write(" - true\n");
                counter++;
            } else {
                writer.write(" - false\n");
            }
        }
        System.out.println("Prediction accuracy: " + (counter / hospitals.length) * 100 + "%");

        writer.close();
    }
}
