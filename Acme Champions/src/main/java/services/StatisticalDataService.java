
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.StatisticalDataRepository;
import domain.StatisticalData;

@Service
@Transactional
public class StatisticalDataService {

	// Managed Repository ------------------------
	@Autowired
	private StatisticalDataRepository	statisticalDataRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService				actorService;


	// Methods -----------------------------------

	public StatisticalData create() {

		StatisticalData result;

		result = new StatisticalData();
		result.setAccumulatedYellowCard(0);
		result.setGoals(0);
		result.setMatchsPlayed(0);
		result.setRedCards(0);
		result.setYellowCards(0);

		return result;

	}

	public StatisticalData save(final StatisticalData data) {

		final StatisticalData result = this.statisticalDataRepository.save(data);

		return result;
	}

	public StatisticalData findStatisticalDataByPlayerId(final int playerId) {

		final StatisticalData res = this.statisticalDataRepository.findStatisticalDataByPlayerId(playerId);

		return res;
	}
}
