package se.fk.github.quarkustemplate.presentation.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface PresentationAlternative
{
   String id();

   String name();
}
