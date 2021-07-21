class Var {
		public String typeVar;
		public String valueVar;
		
		public Var(String typeVar, String valueVar){
			this.typeVar = typeVar;
			this.valueVar = valueVar;
		}
		
		public String getTypeVar(){
			return this.typeVar;
		}
		
		public String getValueVar(){
			return this.valueVar;
		}
		
		@Override
		public String toString(){
			return "["+this.typeVar + " ," + this.valueVar + "]";
		}
	}
