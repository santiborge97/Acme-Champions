package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.DashboardRepository;

@Service
@Transactional
public class DashboardService {
	
	// Managed Repository ---------------------------------------------------
	@Autowired
	private DashboardRepository						dashboardRepository;
	
	// Methods --------------------------------------------------------------
	public Double avgOfTrainingsPerManager() {
		return this.dashboardRepository.avgOfTrainingsPerManager();
	}
	
	public Integer minOfTrainingsPerManager() {
		return this.dashboardRepository.minOfTrainingsPerManager();
	}
	
	public Integer maxOfTrainingsPerManager() {
		return this.dashboardRepository.maxOfTrainingsPerManager();
	}
	
	public Double stdOfTrainingsPerManager() {
		return this.dashboardRepository.stdOfTrainingsPerManager();
	}
	
	public Double avgLengthOfTrainings() {
		return this.dashboardRepository.avgLengthOfTrainings();
	}
	
	public Integer minLengthOfTrainings() {
		return this.dashboardRepository.minLengthOfTrainings();
	}
	
	public Integer maxLengthOfTrainings() {
		return this.dashboardRepository.maxLengthOfTrainings();
	}
	
	public Double stdLengthOfTrainings() {
		return this.dashboardRepository.stdLengthOfTrainings();
	}
	
	public Double avgResultsPerFinder() {
		return this.dashboardRepository.avgResultsPerFinder();
	}
	
	public Integer minResultsPerFinder() {
		return this.dashboardRepository.minResultsPerFinder();
	}
	
	public Integer maxResultsPerFinder() {
		return this.dashboardRepository.maxResultsPerFinder();
	}
	
	public Double stdResultsPerFinder() {
		return this.dashboardRepository.avgResultsPerFinder();
	}
	
	public Double ratioGoalkeepers() {
		return this.dashboardRepository.ratioGoalkeepers();
	}
	
	public Double ratioDefenders() {
		return this.dashboardRepository.ratioDefenders();
	}
	
	public Double ratioMidfielders() {
		return this.dashboardRepository.ratioMidfielders();
	}
	
	public Double ratioStrikers() {
		return this.dashboardRepository.ratioStrikers();
	}
	
	public Double ratioOfManagersWithoutTeam() {
		return this.dashboardRepository.ratioOfManagersWithoutTeam();
	}
	
	public List<String> superiorTeams() {
		return this.dashboardRepository.superiorTeams();
	}
	
	// B & A Level
	
	public Double avgMatchesPerReferee() {
		return this.dashboardRepository.avgMatchesPerReferee();
	}
	
	public Integer minMatchesPerReferee() {
		return this.dashboardRepository.minMatchesPerReferee();
	}
	
	public Integer maxMatchesPerReferee() {
		return this.dashboardRepository.maxMatchesPerReferee();
	}
	
	public Double stdMatchesPerReferee() {
		return this.dashboardRepository.stdMatchesPerReferee();
	}
	
	public Double avgYellowCardsPerPlayer() {
		return this.dashboardRepository.avgYellowCardsPerPlayer();
	}
	
	public Integer minYellowCardsPerPlayer() {
		return this.dashboardRepository.minYellowCardsPerPlayer();
	}
	
	public Integer maxYellowCardsPerPlayer() {
		return this.dashboardRepository.maxYellowCardsPerPlayer();
	}
	
	public Double stdYellowCardsPerPlayer() {
		return this.dashboardRepository.stdYellowCardsPerPlayer();
	}
	
	public List<String> topPlayers() {
		return this.dashboardRepository.topPlayers();
	}
	
	public Double avgTeamsPerCompetition() {
		return this.dashboardRepository.avgTeamsPerCompetition();
	}
	
	public Integer minTeamsPerCompetition() {
		return this.dashboardRepository.minTeamsPerCompetition();
	}
	
	public Integer maxTeamsPerCompetition() {
		return this.dashboardRepository.maxTeamsPerCompetition();
	}

	public Double stdTeamsPerCompetition() {
		return this.dashboardRepository.stdTeamsPerCompetition();
	}
	
	public String oldestFederation() {
		return this.dashboardRepository.oldestFederation();
	}
}
