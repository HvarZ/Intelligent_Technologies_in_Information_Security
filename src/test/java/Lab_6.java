import com.RainCarnation.service.Measurable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Lab_6 {
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
            JSONObject hospital, geoData = null;
            JSONArray coordinates, district = null;
            double x, y;
            String name, area;
            for (int i = 0; i < hospitalsJ.size(); ++i) {
                hospital = (JSONObject) hospitalsJ.get(i);
                name = (String)hospital.get("FullName");

                district = (JSONArray) hospital.get("ObjectAddress");
                area = (String) ((JSONObject) district.get(0)).get("AdmArea");

                geoData = (JSONObject) hospital.get("geoData");
                coordinates = (JSONArray)((JSONArray) geoData.get("coordinates")).get(0);
                x = (double) coordinates.get(0);
                y = (double) coordinates.get(1);

                hospitals[i] = new Hospital(x, y, name, area);
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
        return hospitals;
    }

    static public void main(String[] args) throws ParseException {
        Hospital[] hospitals = parseJsonToHospitals("src/test/resources/Hospitals.json");

        System.out.println(124);
    }
}
