package se.fk.github.quarkustemplate.presentation.dto;

import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
public interface PresentationAlternativesResponse
{
   List<PresentationAlternative> alternatives();
}
