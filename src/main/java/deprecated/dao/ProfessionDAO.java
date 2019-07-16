package deprecated.dao;

import deprecated.database.Database;
import deprecated.models.Profession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfessionDAO extends BaseDAO {
    private static final String name = "profession";
    private static final String[] title = {"level", "code", "name", "father"};
    private static final String[] readableTitle = {"级别", "代码", "专业名", "父专业"};

    protected String getName() {
        return name;
    }

    protected String[] getTitle() {
        return title;
    }

    public String[] getReadableTitle() {
        return readableTitle;
    }

    public ProfessionDAO(Database database) {
        super(database);
    }

    private Profession buildProfessionFromEntryAndFatherList(String[] entry, Iterable<Profession> fathers) {
        Profession father = null;
        for (Profession profession : fathers) {
            if (profession.getName().equals(entry[3])) {
                father = profession;
                break;
            }
        }
        if (father != null) {
            return new Profession(entry[1], entry[2], father);
        } else {
            return new Profession(entry[1], entry[2]);
        }
    }

    public List<Profession> getProfessions() {
        List<String[]> entries = database.getAllEntries(ProfessionDAO.name);
        List<Profession> professions = new ArrayList<>();
        int maxLevel = -1;
        for (String[] entry : entries) {
            int level = Integer.valueOf(entry[0]);
            if (level > maxLevel) {
                maxLevel = level;
            }
        }
        List<Profession> lastRoundProfessions = new ArrayList<>();
        List<Profession> thisRoundProfessions = new ArrayList<>();
        for (int level = 0; level <= maxLevel; ++level) {
            for (String[] entry : entries) {
                int curr = Integer.valueOf(entry[0]);
                if (curr == level) {
                    Profession profession = buildProfessionFromEntryAndFatherList(entry, lastRoundProfessions);
                    professions.add(profession);
                    thisRoundProfessions.add(profession);
                }
            }
            if (level != maxLevel) {
                lastRoundProfessions = thisRoundProfessions;
                thisRoundProfessions = new ArrayList<>();
            }
        }
        return professions;
    }

    public void addProfessions(Iterable<Profession> professions) {
        for (Profession prof : professions) {
            database.addEntry(ProfessionDAO.name, prof.toStringArray());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
