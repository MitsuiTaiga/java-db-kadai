package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {

		Connection con = null;
		Statement statement = null;

		//ポストリスト
		String[][] postsList = {
				{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		try {
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"taigam0519");

			System.out.println("データベース接続成功");

			// SQLクエリを準備
			String sql1 = "INSERT INTO posts (user_id,posted_id,post_content,likes) VALUES (?,?,?,?);";
			PreparedStatement preparedStatement = con.prepareStatement(sql1);

			// リストの1行目から順番に読み込む
			int rowCnt = 0;
			for (int i = 0; i < postsList.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換え
				preparedStatement.setString(1, postsList[i][0]);//ユーザID
				preparedStatement.setString(2, postsList[i][1]);//ポスト日時
				preparedStatement.setString(3, postsList[i][2]);//投稿の内容
				preparedStatement.setString(4, postsList[i][3]);//いいね数

				// SQLクエリを実行（DBMSに送信）
				System.out.println("レコード追加:" + preparedStatement.toString());
				rowCnt = preparedStatement.executeUpdate();
				System.out.println(rowCnt + "件のレコードが追加されました");
			}

			//SQlクエリを準備
			statement = con.createStatement();
			String sql = "SELECT posted_id, post_content, likes FROM posts";

			//SQLクエリを実行（DBMSに送信)
			ResultSet result = statement.executeQuery(sql);

			//SQLクエリの実行結果を抽出
			while (result.next()) {
				Date postid = result.getDate("posted_id");
				String postcontent = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(
						result.getRow() + "件目:" + "投稿日時=" + postid + "/投稿内容=" + postcontent + "いいね数=" + likes);

			}
		} catch (SQLException e) {
			System.out.println("エラー発生:" + e.getMessage());
		} finally {
			//使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}

		}

	}
}
