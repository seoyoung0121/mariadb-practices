package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.UserVo;

public class UserDao {

	public void deleteByNo(Long no) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from user where no = ?");) {

			pstmt.setLong(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void insert(UserVo vo) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert" + " into user " + "values(null, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {

			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getPhone());
			pstmt1.setString(3, vo.getEmail());
			pstmt1.setString(4, vo.getPassword());

			int count = pstmt1.executeUpdate();

			boolean result = count == 1;

			if (result) {
				ResultSet rs = pstmt2.executeQuery();

				if (rs.next()) {
					vo.setNo(rs.getLong(1));
				}
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select no, name, phone, email, password from user order by no desc");) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String email = rs.getString(4);
				String password = rs.getString(5);

				UserVo vo = new UserVo(name, email, password, phone);
				vo.setNo(no);
				result.add(vo);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}


}
