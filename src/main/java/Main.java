import tree.Node;

import java.io.*;

/**
 * Created by daniel on 17.05.15.
 */
public class Main {

    public static void main(String[] args) {
        Parser p = new Parser();

        // Rendern mit: dot -Tpng -o onto.png onto.dot

        try {
            p.load(new File("/Users/daniel/Desktop/onto.owl"));

            Node tree = p.parse();

            Renderer r = new Renderer();

            String dot = r.render(tree);

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("/Users/daniel/Desktop/onto.dot"), "UTF-8"));
            try {
                out.write(dot);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
