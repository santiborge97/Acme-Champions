
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository

	@Autowired
	private BoxRepository	boxRepository;

	// Suporting services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private Validator		validator;


	// Simple CRUD methods

	public Box create() {

		Box result;

		result = new Box();

		return result;

	}

	public Collection<Box> findAll() {

		final Collection<Box> boxes = this.boxRepository.findAll();

		Assert.notNull(boxes);

		return boxes;

	}

	public Box findOne(final int boxID) {

		final Box box = this.boxRepository.findOne(boxID);

		Assert.notNull(box);

		return box;

	}

	public Box save(final Box box) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = box.getActor();
		final String name = box.getName();

		if (box.getId() == 0)
			box.setActor(actor);
		else
			Assert.isTrue(actor.getId() == owner.getId());

		Assert.isTrue(name != "in box");
		Assert.isTrue(name != "out box");
		Assert.isTrue(name != "trash box");
		Assert.isTrue(name != "spam box");

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public Box saveNewActor(final Box box) {

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	// Other business methods

	public Box findTrashBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findTrashBoxByActorId(actorId);
		return result;
	}

	public Box findInBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findInBoxByActorId(actorId);
		return result;
	}

	public Box findOutBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findOutBoxByActorId(actorId);
		return result;
	}

	public Box findSpamBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findSpamBoxByActorId(actorId);
		return result;
	}

	public Collection<Box> findAllBoxByActor(final int actorId) {
		Collection<Box> boxes = new ArrayList<Box>();
		boxes = this.boxRepository.findAllBoxByActorId(actorId);
		return boxes;
	}

	public Boolean boxSecurity(final int boxId) {

		Boolean res = false;

		final Actor owner = this.boxRepository.findOne(boxId).getActor();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}

	public Box reconstruct(final Box box, final BindingResult binding) {

		final Box result = box;

		if (box.getId() == 0) {

			;
			result.setActor(this.actorService.findByPrincipal());

		} else {

			final Box theOldOne = this.findOne(box.getId());

			result.setActor(theOldOne.getActor());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public boolean existId(final int boxId) {
		Boolean res = false;

		final Box box = this.boxRepository.findOne(boxId);

		if (box != null)
			res = true;

		return res;
	}

	public void flush() {
		this.boxRepository.flush();
	}
}
