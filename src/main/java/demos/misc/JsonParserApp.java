package demos.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParserApp {
}

interface JsonElement{
    Object getValue();
}

class JsonString implements JsonElement{
    private String value;

    JsonString(String value){
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}

class JsonNumber implements JsonElement{
    private Number value;

    public JsonNumber(Number value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}

class JsonArray implements JsonElement{
    List<JsonElement> value;

    public JsonArray(List<JsonElement> value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}

class JsonObject implements JsonElement{
    Map<String, JsonElement> value;

    public JsonObject(Map<String, JsonElement> value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        Map<String, Object> result = new HashMap<>();
        value.forEach((key, val) -> result.put(key, val.getValue()));
        return result;
    }
}

class JsonParser{
    private int index;
    private String json;

    // const
    private static final char OPEN_CURLY_BRACE = '{';
    private static final char CLOSE_CURLY_BRACE = '}';
    private static final char OPEN_SQUARE_BRACE = '[';
    private static final char CLOSE_SQUARE_BRACE = ']';
    private static final char DOUBLE_QUOTE = '"';
    private static final char COLON = ':';
    private static final char COMMA = ',';

    public JsonElement parse(String jsonString){
        this.index = 0;
        this.json = jsonString;
        skipWhiteSpace();
        return parseValue();
    }

    private JsonElement parseValue() {
        char curChar = json.charAt(index);

        if (curChar == OPEN_CURLY_BRACE){
            return parseObject();
        }
        else if(curChar == OPEN_SQUARE_BRACE){
            return parseArray();
        }
        else if(curChar == DOUBLE_QUOTE){
            return parseString();
        }
        else if(Character.isDigit(curChar) || curChar == '-'){
            return parseNumber();
        }

        throw new RuntimeException("Invalid JSON");
    }

    void consume(char expected){
        while (json.charAt(index) == expected) index++;
    }

    private JsonElement parseNumber() {
        int startIndex = index;

        // find end index of number
        while (Character.isDigit(json.charAt(index)) || json.charAt(index) == '.') index++;

        String numString = json.substring(startIndex, index);
        if(numString.contains(".")){
            return new JsonNumber(Double.parseDouble(numString));
        }
        else {
            return new JsonNumber(Long.parseLong(numString));
        }
    }

    private JsonElement parseString() {
        consume(DOUBLE_QUOTE);

        StringBuilder sb = new StringBuilder();
        while (json.charAt(index) != DOUBLE_QUOTE){
            sb.append(json.charAt(index++));
        }

        consume(DOUBLE_QUOTE);

        return new JsonString(sb.toString());
    }

    private JsonElement parseArray() {
        List<JsonElement> elements = new ArrayList<>();

        consume(OPEN_SQUARE_BRACE);
        skipWhiteSpace();

        while (json.charAt(index) != CLOSE_SQUARE_BRACE){
            JsonElement element = parseValue();
            elements.add(element);

            skipWhiteSpace();

            if (json.charAt(index) == COMMA){
                consume(COMMA);
                skipWhiteSpace();
            }
        }

        consume(CLOSE_SQUARE_BRACE);

        return new JsonArray(elements);
    }

    private JsonElement parseObject() {
        Map<String, JsonElement> props = new HashMap<>();

        consume(OPEN_CURLY_BRACE);
        skipWhiteSpace();

        while (json.charAt(index) != CLOSE_CURLY_BRACE){
            String propName = parseString().getValue().toString();

            consume(COLON);
            skipWhiteSpace();

            JsonElement propValue = parseValue();
            props.put(propName, propValue);

            if (json.charAt(index) != COMMA){
                consume(COMMA);
                skipWhiteSpace();
            }
        }

        consume(CLOSE_CURLY_BRACE);

        return new JsonObject(props);
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(this.json.charAt(this.index))) this.index++;
    }

    public static void main(String[] args) {
        String jsonString = "{ \"name\": \"John\", \"age\": 30, \"city\": \"New York\", \"isAdmin\": true, \"scores\": [10, 20, 30] }";


        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);

        System.out.println(jsonElement.getValue());
    }
}