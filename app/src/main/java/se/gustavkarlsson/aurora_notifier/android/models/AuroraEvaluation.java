package se.gustavkarlsson.aurora_notifier.android.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import static java8.util.stream.StreamSupport.stream;

@Parcel
public class AuroraEvaluation {
	long timestampMillis;
	AuroraData data;
	List<AuroraComplication> complications;
	AuroraChance chance;

	AuroraEvaluation() {
	}

	public AuroraEvaluation(long timestampMillis, AuroraData data, List<AuroraComplication> complications) {
		this.timestampMillis = timestampMillis;
		this.data = data;
		this.complications = complications != null ?
				new ArrayList<>(complications) :
				new ArrayList<>();
		this.chance = calculateChance(complications);
	}

	private static AuroraChance calculateChance(List<AuroraComplication> complications) {
		return stream(complications)
				.map(AuroraComplication::getChance)
				.reduce((c1, c2) -> c1.ordinal() > c2.ordinal() ? c1 : c2)
				.orElse(AuroraChance.HIGH);
	}

	public long getTimestampMillis() {
		return timestampMillis;
	}

	public AuroraData getData() {
		return data;
	}

	public List<AuroraComplication> getComplications() {
		return complications;
	}

	public AuroraChance getChance() {
		return chance;
	}

	@Override
	public String toString() {
		return "AuroraEvaluation{" +
				"timestampMillis=" + timestampMillis +
				", data=" + data +
				", complications=" + complications +
				", chance=" + chance +
				'}';
	}
}
