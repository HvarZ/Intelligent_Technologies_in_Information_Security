import com.RainCarnation.service.Measurable;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Lab_6 {
    public class Hospital implements Measurable {
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


    static public void main(String[] args) throws ParseException {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("/Users/hvarz/IdeaProjects/Intelligent_Technologies/src/test/resources/Hospitals.json")) {
            JSONArray hospitals = (JSONArray) parser.parse(reader);
            System.out.println("Hello world");
        } catch (IOException exception ) {
            exception.printStackTrace();
        }
    }
}
