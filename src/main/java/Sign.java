import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;

public  class Sign {
	private int id;
	private Timestamp timein;
	private int studentId;
//constructor
	public Sign(int studentId) {
		this.studentId = studentId;


		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO signs (timein, studentId) VALUES (now(), :studentId)";
			this.id = (int) con.createQuery(sql, true)
			 .addParameter("studentId", this.studentId)
			 .executeUpdate()
			 .getKey();
		}
	}
	//getter methods

		public int getStudentId() {
			 return studentId;
		 }

		 //returns the timein
		 public Timestamp getTimein() {
			 return timein;
		 }
		 //returns the id of that sign in
		 public int getId(){
	     return id;
	   }
		 public List<Individual> getStudents() {
 			try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT * FROM individuals where groupId=:id";
         return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Individual.class);
         }
 		}
		public Individual getStudent() {
             return Individual.find(studentId);
         }


		@Override
	 public boolean equals(Object otherSign){
		 if (!(otherSign instanceof Sign)) {
			 return false;
		 } else {
			 Sign newSign = (Sign) otherSign;
			 return this.getStudentId() == newSign.getStudentId();
		 }
	 }
	 // saves in the database
	 public void save() {
		 try(Connection con = DB.sql2o.open()) {
 			String sql = "INSERT INTO signs (timein, studentId) VALUES (now(), :studentId)";
 			this.id = (int) con.createQuery(sql, true)
 			 .addParameter("studentId", this.studentId)
 			 .executeUpdate()
 			 .getKey();
 		}
	 }
	 //retrieving all the sings from the database
	 public static List<Sign> all() {
		 String sql = "SELECT * FROM signs;";
		 try(Connection con = DB.sql2o.open()) {
			 return con.createQuery(sql)
			 .executeAndFetch(Sign.class);
		 }
	 }
	 //find a signin by id
	 public static Sign find(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM signs where id=:id";
			Sign signin = con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Sign.class);
			return signin;
		}
	 }

 }
