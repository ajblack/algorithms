import
java.sql.*;
public
class
jdbcexample{
    static String url ="jdbc:mysql://localhost:3306/ajblack?user=ajblack&password=s3cr3t201e";
    public
    static
    void
    main
    (String[]
     args)
    throws
    Exception{
        Connection
        con
        =
        null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(java.lang.ClassNotFoundException
              e){
            System.err.print("ClassNotFoundException:");
            System.err.print(e.getMessage());
        }
        try{
            con
            =
            DriverManager.getConnection(url);
            System.out.println("Got
                               Connection.");
                               }
                               catch(SQLException
                                     ex)
            {
                System.err.println("SQLException:
                                   "
                                   +
                                   ex.getMessage());}
                               }
                               }