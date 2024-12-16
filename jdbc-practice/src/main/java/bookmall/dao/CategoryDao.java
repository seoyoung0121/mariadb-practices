package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {

	public void insert(CategoryVo vo) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert" + " into category " + "values(null, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {

			pstmt1.setString(1, vo.getName());

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
	
	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select no, name from category order by no desc");) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				CategoryVo vo = new CategoryVo(no, name);
				result.add(vo);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}
	
	public void deleteByNo(Long no) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from category where no = ?");) {

			pstmt.setLong(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
}
