package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface DashboardRepository extends JpaRepository<Administrator, Integer>{
	
	// C Level Queries

	@Query(nativeQuery= true, value= "select avg(count) from (select count(*) as Count "
			+ "from training t join manager m on (m.id = t.manager) "
			+ "group by manager) as counts")
	Double avgOfTrainingsPerManager();
	
	@Query(nativeQuery= true, value= "select min(count) from (select count(*) as Count "
			+ "from training t join manager m on (m.id = t.manager) "
			+ "group by manager) as counts")
	Integer minOfTrainingsPerManager();
	
	@Query(nativeQuery= true, value= "select max(count) from (select count(*) as Count "
			+ "from training t join manager m on (m.id = t.manager) "
			+ "group by manager) as counts")
	Integer maxOfTrainingsPerManager();
	
	@Query(nativeQuery= true, value= "select std(count) from (select count(*) as Count "
			+ "from training t join manager m on (m.id = t.manager) "
			+ "group by manager) as counts")
	Double stdOfTrainingsPerManager();
	
	@Query(nativeQuery= true, value= "select avg(duration) from (select TIMESTAMPDIFF(MINUTE,t.start_date,t.ending_date) as duration from training t) as duration")
	Double avgLengthOfTrainings();
	
	@Query(nativeQuery= true, value= "select min(duration) from (select TIMESTAMPDIFF(MINUTE,t.start_date,t.ending_date) as duration from training t) as duration")
	Integer minLengthOfTrainings();
	
	@Query(nativeQuery= true, value= "select max(duration) from (select TIMESTAMPDIFF(MINUTE,t.start_date,t.ending_date) as duration from training t) as duration")
	Integer maxLengthOfTrainings();
	
	@Query(nativeQuery= true, value= "select std(duration) from (select TIMESTAMPDIFF(MINUTE,t.start_date,t.ending_date) as duration from training t) as duration")
	Double stdLengthOfTrainings();
	
	@Query(nativeQuery=true, value= "select avg(count) from (select f.id, count(fp.finder) as Count from finder f left join finder_players fp on (f.id = fp.finder) left join finder_managers fm on (f.id = fm.finder) group by f.id ) as counts")
	Double avgResultsPerFinder();
	
	@Query(nativeQuery=true, value= "select min(count) from (select f.id, count(fp.finder) as Count from finder f left join finder_players fp on (f.id = fp.finder) left join finder_managers fm on (f.id = fm.finder) group by f.id ) as counts")
	Integer minResultsPerFinder();
	
	@Query(nativeQuery=true, value= "select max(count) from (select f.id, count(fp.finder) as Count from finder f left join finder_players fp on (f.id = fp.finder) left join finder_managers fm on (f.id = fm.finder) group by f.id ) as counts")
	Integer maxResultsPerFinder();
	
	@Query(nativeQuery=true, value= "select std(count) from (select f.id, count(fp.finder) as Count from finder f left join finder_players fp on (f.id = fp.finder) left join finder_managers fm on (f.id = fm.finder) group by f.id ) as counts")
	Double stdResultsPerFinder();
	
	@Query(nativeQuery=true, value= "select (select count(*) from player where player.position_english='GOALKEEPER')/(select count(*) from player)")
	Double ratioGoalkeepers();
	
	@Query(nativeQuery=true, value= "select (select count(*) from player where player.position_english='DEFENDER')/(select count(*) from player)")
	Double ratioDefenders();
	
	@Query(nativeQuery=true, value= "select (select count(*) from player where player.position_english='MIDFIELDER')/(select count(*) from player)")
	Double ratioMidfielders();

	@Query(nativeQuery=true, value= "select (select count(*) from player where player.position_english='STRIKER')/(select count(*) from player)")
	Double ratioStrikers();
	
	@Query(nativeQuery=true, value= "select (select count(*) from manager where manager.team is null)/(select count(*) from manager)")
	Double ratioOfManagersWithoutTeam();
	
	@Query(nativeQuery=true, value= "select name from (select t.name as name, count(p.id) as Count from player p right join team t on (t.id = p.team) group by t.id) as counts where Count > (select avg(count) * 0.1 + avg(count) from (select t.id, count(p.id) as Count from player p right join team t on (t.id = p.team) group by t.id) as counts)")
	List<String> superiorTeams();
	
	// B & A Level Queries
	
	@Query(nativeQuery=true, value= "select avg(count) from (select count(*) as Count from game g join referee r on (r.id = g.referee) group by referee) as counts")
	Double avgMatchesPerReferee();
	
	@Query(nativeQuery=true, value= "select min(count) from (select count(*) as Count from game g join referee r on (r.id = g.referee) group by referee) as counts")
	Integer minMatchesPerReferee();
	
	@Query(nativeQuery=true, value= "select max(count) from (select count(*) as Count from game g join referee r on (r.id = g.referee) group by referee) as counts")
	Integer maxMatchesPerReferee();
	
	@Query(nativeQuery=true, value= "select std(count) from (select count(*) as Count from game g join referee r on (r.id = g.referee) group by referee) as counts")
	Double stdMatchesPerReferee();
	
	@Query(nativeQuery=true, value="select avg(yellow_cards) from statistical_data")
	Double avgYellowCardsPerPlayer();
	
	@Query(nativeQuery=true, value="select min(yellow_cards) from statistical_data")
	Integer minYellowCardsPerPlayer();
	
	@Query(nativeQuery=true, value="select max(yellow_cards) from statistical_data")
	Integer maxYellowCardsPerPlayer();
	
	@Query(nativeQuery=true, value="select std(yellow_cards) from statistical_data")
	Double stdYellowCardsPerPlayer();
	
	@Query(nativeQuery=true, value="select p.name from statistical_data s join player p on (p.id = s.player) group by player order by s.goals desc limit 5")
	List<String> topPlayers();
	
	@Query(nativeQuery=true, value="select avg(count) from (select count(*) as count from competition c join competition_teams t on (c.id = t.competition) group by c.id) as counts")
	Double avgTeamsPerCompetition();
	
	@Query(nativeQuery=true, value="select min(count) from (select count(*) as count from competition c join competition_teams t on (c.id = t.competition) group by c.id) as counts")
	Integer minTeamsPerCompetition();
	
	@Query(nativeQuery=true, value="select max(count) from (select count(*) as count from competition c join competition_teams t on (c.id = t.competition) group by c.id) as counts")
	Integer maxTeamsPerCompetition();
	
	@Query(nativeQuery=true, value="select std(count) from (select count(*) as count from competition c join competition_teams t on (c.id = t.competition) group by c.id) as counts")
	Double stdTeamsPerCompetition();
	
	@Query(nativeQuery=true, value="select name from federation order by establishment_date asc limit 1")
	String oldestFederation();
	
}
