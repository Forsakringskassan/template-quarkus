package se.fk.github.quarkustemplate.presentation.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface PresentationOmbudRequest
{
   String personnummer();
}
