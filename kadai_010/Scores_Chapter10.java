package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Connection  con = null;
		Statement statement = null;
		
		try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "taigam0519"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備(更新)
            statement = con.createStatement();
            String sql = "UPDATE scores SET score_math = 95 , score_english = '80' WHERE id = 5;";

            // SQLクエリを実行（DBMSに送信）
            System.out.println("レコード更新を実行します");
            int rowCnt = statement.executeUpdate(sql);
            System.out.println( rowCnt + "件のレコードが更新されました");
            
            //SQLクエリを準備（並べ替え）
            statement = con.createStatement();
            String sql2 = "SELECT * FROM scores ORDER BY score_math ASC, score_english ASC;";
            
            
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            ResultSet result = statement.executeQuery(sql2);
            
            while(result.next()) {
            	int id = result.getInt("id");
            	String name = result.getString("name"); 
                int math = result.getInt("score_math");
                String english = result.getString("score_english");
                System.out.println(result.getRow() + "件目：生徒Id=" + id
                                   + "/氏名=" + name + "/数学=" + math + "/英語" + english );
            }
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }

	}

}
