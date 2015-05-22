
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import tree.Node;
import tree.NodeCache;
import tree.Relation;

import java.io.*;
import java.util.*;

/**
 * Created by daniel on 17.05.15.
 */
public class Parser {

    private OntModel model;
    private NodeCache nodes = new NodeCache();

    public void load(File owlFile) throws IOException {
        if(!owlFile.exists()) {
            throw new FileNotFoundException("File " + owlFile.getAbsolutePath() + " does not exist");
        }

        Model m = ModelFactory.createDefaultModel();

        InputStream is = new FileInputStream(owlFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
        //m.read(reader, "RDF/XML-ABBREV", null);

        OntModel ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC, m);
        //FileManager.get().readModel(ontModel, owlFile.getAbsolutePath()) ;

        ontModel.read(reader, "RDF/XML-ABBREV", null);
        ontModel.prepare();

        reader.close();
        is.close();


        this.model = ontModel;
    }

    public Node parse() {
        Node thing = nodes.getOrCreateNode(OWL.Thing.getURI(), "Ding");

        OntClass thingClass = model.getOntClass(OWL.Thing.getURI());
        addSubclassesToNode(thing, thingClass);
        addRelationsToNode(thing, thingClass);

        nodes.clear();
        return thing;
    }

    private void addRelationsToNode(Node root, OntClass clazz) {
        Node domain = nodes.getOrCreateNode(clazz.getURI(), clazz.getLocalName());
        ExtendedIterator<OntProperty> props = clazz.listDeclaredProperties(true);
        while(props.hasNext()) {
            OntProperty prop = props.next();
            if(prop.isObjectProperty()) {
                ExtendedIterator<? extends OntResource> rangeIter = prop.listRange();
                while(rangeIter.hasNext()) {
                    OntResource orRange = rangeIter.next();
                    if(orRange.isClass()) {


                        Node range = nodes.getOrCreateNode(orRange.getURI(), orRange.getLocalName());
                        Relation r = new Relation(domain, range, prop.getURI(), prop.getLocalName());
                        root.addRelation(r);
                    }
                }

                rangeIter.close();
            }
        }

        props.close();
    }

    private void addSubclassesToNode(Node root, OntClass clazz) {
        ExtendedIterator<OntClass> subclasses = clazz.listSubClasses(true);
        Set<OntClass> toProcess = new HashSet<>();
        while(subclasses.hasNext()) {
            OntClass subClass = subclasses.next();
            toProcess.add(subClass);
        }

        subclasses.close();

        for(OntClass subClass : toProcess) {
            if(subClass.getURI().equals(OWL.Nothing.getURI())) {
                continue;
            }

            Node subNode = nodes.getOrCreateNode(subClass.getURI(), subClass.getLocalName());
            addSubclassesToNode(subNode, subClass);
            root.addChild(subNode);
        }

        addRelationsToNode(root, clazz);
    }
}
