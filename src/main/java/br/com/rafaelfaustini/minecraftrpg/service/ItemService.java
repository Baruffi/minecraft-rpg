package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rafaelfaustini.minecraftrpg.dao.ItemDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.LoreDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class ItemService {

    public ItemEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        ItemEntity item = null;

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            item = itemDAO.get(id);

            if (item != null) {
                List<String> lore = getItemLore(item, con);

                item.setLore(lore);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return item;
    }

    public ItemEntity getByName(String name) {
        SqliteConnection sql = new SqliteConnection();
        ItemEntity item = null;

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            item = itemDAO.getByName(name);

            if (item != null) {
                List<String> lore = getItemLore(item, con);

                item.setLore(lore);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return item;
    }

    public List<ItemEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<ItemEntity> items = null;

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            items = itemDAO.getAll();

            for (ItemEntity item : items) {
                List<String> lore = getItemLore(item, con);

                item.setLore(lore);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return items;
    }

    public void insert(ItemEntity itemEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            itemDAO.insert(itemEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void update(ItemEntity itemEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            itemDAO.update(itemEntity);
            itemDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void delete(Long id) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ItemDAO itemDAO = new ItemDAO(con);

            itemDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete ItemEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    private List<String> getItemLore(ItemEntity item, Connection con) throws Exception {
        LoreDAO loreDAO = new LoreDAO(con);

        return loreDAO.getAllByItem(item.getId()).stream().map(lore -> lore.getLore()).collect(Collectors.toList()); // hmmmmm...
    }
}
