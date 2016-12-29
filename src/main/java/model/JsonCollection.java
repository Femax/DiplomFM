package model;

import java.util.ArrayList;

/**
 * Created by femax on 29.12.2016.
 */
public class JsonCollection {


        ArrayList<Molecule> molecules;

        public JsonCollection() {
            this.molecules = new ArrayList<Molecule>();
        }

        public void setMolecules(ArrayList<Molecule> emps ) {
            this.molecules = emps;
        }

        public ArrayList<Molecule> getMolecules() {
            return this.molecules;
        }

        public void addHisab( Molecule h ) {
            this.molecules.add( h );
        }


}
