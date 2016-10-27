package com.thinkbiganalytics.nifi.v0.rest.model;

import com.thinkbiganalytics.nifi.rest.model.NiFiAllowableValue;
import com.thinkbiganalytics.nifi.rest.model.NiFiPropertyDescriptor;
import com.thinkbiganalytics.nifi.rest.model.NiFiPropertyDescriptorTransform;

import org.apache.nifi.web.api.dto.PropertyDescriptorDTO;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

/**
 * Transforms {@link NiFiPropertyDescriptor} objects for NiFi v0.6.
 */
public class NiFiPropertyDescriptorTransformV0 implements NiFiPropertyDescriptorTransform {

    @Override
    public Boolean isSensitive(@Nonnull final PropertyDescriptorDTO propertyDescriptorDTO) {
        return propertyDescriptorDTO.isSensitive();
    }

    /**
     * Transforms the specified {@link PropertyDescriptorDTO.AllowableValueDTO} to a {@link NiFiAllowableValue}.
     *
     * @param dto the allowable value DTO
     * @return the NiFi allowable value
     */
    @Nonnull
    public NiFiAllowableValue toNiFiAllowableValue(@Nonnull final PropertyDescriptorDTO.AllowableValueDTO dto) {
        final NiFiAllowableValue nifi = new NiFiAllowableValue();
        nifi.setDisplayName(dto.getDisplayName());
        nifi.setValue(dto.getValue());
        nifi.setDescription(dto.getDescription());
        return nifi;
    }

    @Nonnull
    @Override
    public NiFiPropertyDescriptor toNiFiPropertyDescriptor(@Nonnull final PropertyDescriptorDTO dto) {
        final NiFiPropertyDescriptor nifi = new NiFiPropertyDescriptor();
        nifi.setName(dto.getName());
        nifi.setDisplayName(dto.getDisplayName());
        nifi.setDescription(dto.getDescription());
        nifi.setDefaultValue(dto.getDefaultValue());
        nifi.setRequired(dto.isRequired());
        nifi.setSensitive(dto.isSensitive());
        nifi.setDynamic(dto.isDynamic());
        nifi.setSupportsEl(dto.getSupportsEl());
        nifi.setIdentifiesControllerService(dto.getIdentifiesControllerService());

        final List<PropertyDescriptorDTO.AllowableValueDTO> allowableValues = dto.getAllowableValues();
        if (allowableValues != null) {
            nifi.setAllowableValues(allowableValues.stream().map(this::toNiFiAllowableValue).collect(Collectors.toList()));
        }

        return nifi;
    }
}
