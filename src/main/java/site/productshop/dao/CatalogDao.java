package site.productshop.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import site.productshop.entities.Category;
import site.productshop.entities.Item;

public class CatalogDao extends BaseDao {

	public Category getCategoryByID(int id) throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `parent`, `icon`, `slug`, `creating_date` FROM `categories` "
		+ "WHERE `id` = ? AND `is_deleted` = 0 ";
		Category category = null;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				category = new Category(resultSet.getString("name"), resultSet.getString("slug"));
				int parentID = resultSet.getInt("parent");
				Category parent = parentID > 0 ? getCategoryByID(parentID) : null;
				category.setParent(parent);
				category.setId(resultSet.getInt("id"));
				category.setIcon(resultSet.getString("icon"));
				category.setCreatingDate(resultSet.getDate("creating_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return category;
	}
	
	public ArrayList<Category> getCategoriesByParentCategory(Category parentCategory) throws DaoException {
		int parentCategoryID = parentCategory.getId();
		String sql = 
		"SELECT `id`, `name`, `parent`, `icon`, `slug`, `creating_date` FROM `categories` "
		+ "WHERE `parent` = ? AND `is_deleted` = 0";
		ArrayList<Category> categories = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, parentCategoryID);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Category category = new Category(resultSet.getString("name"), resultSet.getString("slug"));
				category.setParent(parentCategory);
				category.setId(resultSet.getInt("id"));
				category.setIcon(resultSet.getString("icon"));
				category.setCreatingDate(resultSet.getDate("creating_date"));
				categories.add(category);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return categories;
	}
	
	public Category getCategoryBySlug(String categorySlug) throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `parent`, `icon`, `slug`, `creating_date` FROM `categories` "
		+ "WHERE `slug` = ? AND `is_deleted` = 0 ";
		Category category = null;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, categorySlug);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				category = new Category(resultSet.getString("name"), resultSet.getString("slug"));
				int parentID = resultSet.getInt("parent");
				Category parent = parentID > 0 ? getCategoryByID(parentID) : null;
				category.setParent(parent);
				category.setId(resultSet.getInt("id"));
				category.setIcon(resultSet.getString("icon"));
				category.setCreatingDate(resultSet.getDate("creating_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return category;
	}
	
	public ArrayList<Item> getItemsBySubcategoryID(int categoryID, int sortMode, int limit, int offset)
			throws DaoException {
		String sortField = "addition_date DESC";
		if(sortMode == 2) {
			sortField = "price";
		} else if(sortMode == 3) {
			sortField = "price DESC";
		}
		String sql = 
				"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
				+ "`addition_date` FROM `items` WHERE category_id=? AND `is_deleted`=0 ORDER BY "
						+ sortField + " LIMIT " + limit + " OFFSET " + offset;
		ArrayList<Item> items = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, categoryID);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				Item item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return items;
	}
	
	public ArrayList<Item> getItemsByKeyword(String keyword, int sortMode, int limit, int offset) 
			throws DaoException {
		String sortField = "addition_date DESC";
		if(sortMode == 2) {
			sortField = "price";
		} else if(sortMode == 3) {
			sortField = "price DESC";
		}
		String sql = 
				"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
				+ "`addition_date` FROM `items` WHERE `is_deleted`=0 AND `name` LIKE '%"+keyword+"%' ORDER BY "
						+ sortField + " LIMIT " + limit + " OFFSET " + offset;
		ArrayList<Item> items = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				Item item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return items;
	}
	
	public ArrayList<Category> getAllCategories() throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `parent`, `icon`, `slug`, `creating_date` FROM `categories` "
		+ "WHERE `parent` = 0 AND `is_deleted` = 0 ";
		ArrayList<Category> categories = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Category category = new Category(resultSet.getString("name"), resultSet.getString("slug"));
				category.setId(resultSet.getInt("id"));
				category.setIcon(resultSet.getString("icon"));
				category.setCreatingDate(resultSet.getDate("creating_date"));
				categories.add(category);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return categories;
	}
	
	public ArrayList<Item> getAllItems() throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
		+ "`addition_date` FROM `items` WHERE `is_deleted` = 0 ORDER BY `addition_date` DESC";
		ArrayList<Item> items = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				Item item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return items;
	}
	
	public ArrayList<Item> getLastItems(int quantity) throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
		+ "`addition_date` FROM `items` WHERE `is_deleted` = 0 ORDER BY `addition_date` DESC LIMIT ?";
		ArrayList<Item> items = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, quantity);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				Item item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return items;
	}
	
	public ArrayList<Item> getLastDiscountItems(int quantity) throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
		+ "`addition_date` FROM `items` WHERE `is_deleted` = 0 AND sale>0 ORDER BY `addition_date` DESC LIMIT ?";
		ArrayList<Item> items = new ArrayList<>();
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, quantity);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				Item item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return items;
	}
	
	public int getItemQuantityByCategory(Category category) throws DaoException {
		String sql = "SELECT COUNT(*) from `items` WHERE `category_id` = " + category.getId();
		int itemQuantity = 0;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				itemQuantity = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return itemQuantity;
	}
	
	public int getItemQuantityByKeyword(String keyword) throws DaoException {
		String sql = "SELECT COUNT(*) from `items` WHERE `name` LIKE '%"+keyword+"%'";
		int itemQuantity = 0;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				itemQuantity = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return itemQuantity;
	}

	public long addCategory(Category category) throws DaoException {
		String name = category.getName();
		Category parent = category.getParent();
		int parentID = (parent != null) ? parent.getId() : 0;
		String icon = category.getIcon();
		String slug = category.getSlug();
		String sql = "INSERT INTO `categories` (name, parent, icon, slug) "
				+ "VALUES (?, ?, ?, ?)";
		long resultID = 0;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setInt(2, parentID);
			statement.setString(3, icon);
			statement.setString(4, slug);
			statement.executeUpdate();
	        try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                resultID = generatedKeys.getLong(1);
	            }
	        }
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return resultID;
	}
	
	public long addItem(Item item) throws DaoException {
		String name = item.getName();
		String description = item.getDescription();
		BigDecimal price = item.getPrice();
		String photo = item.getPhoto();
		BigDecimal sale = item.getSale();
		int categoryID = item.getCategory().getId();
		String sql = "INSERT INTO `items` (name, category_id, description, photo, price, sale) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		long result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setInt(2, categoryID);
			statement.setString(3, description);
			statement.setString(4, photo);
			statement.setInt(5, (int)(price.doubleValue() * 100));
			statement.setInt(6, sale != null ? sale.intValue() : 0);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int editCategory(Category category, String oldSlug) throws DaoException {
		String name = category.getName();
		String slug = category.getSlug();
		String sql = "UPDATE `categories` SET name=?, slug=? WHERE slug=?";
		int result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setString(2, slug);
			statement.setString(3, oldSlug);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int editSubcategory(Category subcategory, String oldSlug) throws DaoException {
		String name = subcategory.getName();
		String slug = subcategory.getSlug();
		String icon = subcategory.getIcon();
		String sql = "UPDATE `categories` SET name=?, slug=?, icon=? WHERE slug=?";
		int result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setString(2, slug);
			statement.setString(3, icon);
			statement.setString(4, oldSlug);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public Item getItemByID(int itemID) throws DaoException {
		String sql = 
		"SELECT `id`, `name`, `category_id`, `description`, `photo`, `price`, `sale`,"
		+ "`addition_date` FROM `items` WHERE `id` = ? AND `is_deleted` = 0";
		Item item = null;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, itemID);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				double price = ((double)resultSet.getInt("price")) / 100;
				item = new Item(
					resultSet.getString("name"),
					resultSet.getString("description"),
					new BigDecimal(price)	
				);
				item.setId(resultSet.getInt("id"));
				Category category = getCategoryByID(resultSet.getInt("category_id"));
				item.setCategory(category);
				item.setPhoto(resultSet.getString("photo"));
				item.setSale(new BigDecimal(resultSet.getInt("sale")));
				item.setAdditionDate(resultSet.getDate("addition_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return item;
	}
	
	public int editItem(Item item) throws DaoException {
		int itemID = item.getId();
		Category category = item.getCategory();
		String name = item.getName();
		BigDecimal price = item.getPrice();
		BigDecimal sale =  item.getSale();
		String description = item.getDescription();
		String photo = item.getPhoto();
		String sql = "UPDATE `items` SET name=?, category_id=?, description=?, photo=?, price=?, sale=? WHERE id=?";
		int result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setInt(2, category.getId());
			statement.setString(3, description);
			statement.setString(4, photo);
			statement.setInt(5, (int)(item.getPrice().doubleValue() * 100));
			statement.setInt(6, sale.intValue());
			statement.setInt(7, itemID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int deleteCategoryBySlug(String categorySlug) throws DaoException {
		String sql = "UPDATE `categories` SET is_deleted=1 WHERE slug=?";
		int result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, categorySlug);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int deleteItemByID(int itemID) throws DaoException {
		String sql = "UPDATE `items` SET is_deleted=1 WHERE id=?";
		int result;
		try(Connection connection = getJDBCConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, itemID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
}