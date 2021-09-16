package instructions;
    /**
     * Funcion para obtener la longitud de un array o un string
     * Se debe de aplicar sobre el valor de la variable en el que se guarda
     */
    public class Length {
        private String varName;

        public Length (String varName){
            this.varName = varName;
        }

        public String get_var_name (){
            return this.varName;
        }
    }