package analyzer;

public record MethodSignature(String name, String descriptor) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MethodSignature)) {
            return false;
        }
        MethodSignature that = (MethodSignature) o;
        return name.equals(that.name)
                && descriptor.equals(that.descriptor);
    }

}
